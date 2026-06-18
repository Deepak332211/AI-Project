import { STATUS_IDS } from './statuses';
import { todayISO } from './date';

export const createJobId = () =>
  crypto?.randomUUID ? crypto.randomUUID() : `job-${Date.now()}-${Math.random()}`;

export const createEmptyJob = (status = 'wishlist', order = Date.now()) => ({
  id: createJobId(),
  companyName: '',
  jobTitle: '',
  linkedinUrl: '',
  resumeUsed: '',
  appliedDate: todayISO(),
  salaryRange: '',
  notes: '',
  status,
  order,
  createdAt: new Date().toISOString(),
  updatedAt: new Date().toISOString()
});

export const normalizeJob = (job, fallbackOrder = Date.now()) => ({
  id: typeof job.id === 'string' && job.id ? job.id : createJobId(),
  companyName: String(job.companyName ?? '').trim(),
  jobTitle: String(job.jobTitle ?? '').trim(),
  linkedinUrl: String(job.linkedinUrl ?? '').trim(),
  resumeUsed: String(job.resumeUsed ?? '').trim(),
  appliedDate: String(job.appliedDate ?? todayISO()).slice(0, 10),
  salaryRange: String(job.salaryRange ?? '').trim(),
  notes: String(job.notes ?? '').trim(),
  status: STATUS_IDS.includes(job.status) ? job.status : 'wishlist',
  order: Number.isFinite(Number(job.order)) ? Number(job.order) : fallbackOrder,
  createdAt: job.createdAt ?? new Date().toISOString(),
  updatedAt: new Date().toISOString()
});

export const validateJob = (job) => {
  const errors = {};

  if (!job.companyName.trim()) {
    errors.companyName = 'Company name is required.';
  }

  if (!job.jobTitle.trim()) {
    errors.jobTitle = 'Job title is required.';
  }

  if (job.linkedinUrl.trim()) {
    try {
      const url = new URL(job.linkedinUrl);
      if (!['http:', 'https:'].includes(url.protocol)) {
        errors.linkedinUrl = 'Use a valid http or https URL.';
      }
    } catch {
      errors.linkedinUrl = 'Use a valid LinkedIn URL.';
    }
  }

  return errors;
};
