import { useCallback, useEffect, useMemo, useState } from 'react';
import { jobRepository } from '../db/jobRepository';
import { normalizeJob } from '../utils/jobs';
import { STATUSES } from '../utils/statuses';

const byOrder = (a, b) => (a.order ?? 0) - (b.order ?? 0);

const reorderWithinStatus = (jobs, activeId, overId, targetStatus) => {
  const active = jobs.find((job) => job.id === activeId);
  if (!active) return jobs;

  const remaining = jobs.filter((job) => job.id !== activeId);
  const targetJobs = remaining.filter((job) => job.status === targetStatus).sort(byOrder);
  const overIndex = targetJobs.findIndex((job) => job.id === overId);
  const insertIndex = overIndex >= 0 ? overIndex : targetJobs.length;
  const moved = { ...active, status: targetStatus };

  targetJobs.splice(insertIndex, 0, moved);

  const normalizedTarget = targetJobs.map((job, index) => ({
    ...job,
    order: index,
    updatedAt: new Date().toISOString()
  }));

  const byId = new Map(normalizedTarget.map((job) => [job.id, job]));
  return remaining
    .map((job) => byId.get(job.id) ?? job)
    .concat(byId.has(activeId) ? [byId.get(activeId)] : [])
    .filter((job, index, collection) => collection.findIndex((item) => item.id === job.id) === index);
};

export function useJobs() {
  const [jobs, setJobs] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    let isMounted = true;

    jobRepository
      .getAll()
      .then((storedJobs) => {
        if (isMounted) {
          setJobs(storedJobs.map((job, index) => normalizeJob(job, index)));
        }
      })
      .catch(() => {
        if (isMounted) {
          setError('Unable to load IndexedDB data.');
        }
      })
      .finally(() => {
        if (isMounted) {
          setIsLoading(false);
        }
      });

    return () => {
      isMounted = false;
    };
  }, []);

  const jobsByStatus = useMemo(
    () =>
      STATUSES.reduce((groups, status) => {
        groups[status.id] = jobs.filter((job) => job.status === status.id).sort(byOrder);
        return groups;
      }, {}),
    [jobs]
  );

  const saveJob = useCallback(async (job) => {
    const normalized = normalizeJob(job);
    await jobRepository.save(normalized);
    setJobs((current) => {
      const exists = current.some((item) => item.id === normalized.id);
      return exists
        ? current.map((item) => (item.id === normalized.id ? normalized : item))
        : [...current, normalized];
    });
    return normalized;
  }, []);

  const deleteJob = useCallback(async (id) => {
    await jobRepository.delete(id);
    setJobs((current) => current.filter((job) => job.id !== id));
  }, []);

  const moveJob = useCallback(async (activeId, overId, overType) => {
    let nextJobs = [];

    setJobs((current) => {
      const targetStatus = overType === 'column' ? overId : current.find((job) => job.id === overId)?.status;
      if (!targetStatus) return current;
      nextJobs = reorderWithinStatus(current, activeId, overId, targetStatus);
      return nextJobs;
    });

    if (nextJobs.length) {
      await jobRepository.saveMany(nextJobs);
    }
  }, []);

  const importJobs = useCallback(async (incomingJobs) => {
    const normalized = incomingJobs.map((job, index) => normalizeJob(job, index));
    await jobRepository.replaceAll(normalized);
    setJobs(normalized);
  }, []);

  return {
    jobs,
    jobsByStatus,
    isLoading,
    error,
    saveJob,
    deleteJob,
    moveJob,
    importJobs
  };
}
