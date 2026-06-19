import { JOB_STORE, getDatabase } from './database';

export const jobRepository = {
  async getAll() {
    const db = await getDatabase();
    const jobs = await db.getAll(JOB_STORE);
    return jobs.sort((a, b) => {
      if (a.status === b.status) {
        return (a.order ?? 0) - (b.order ?? 0);
      }
      return String(a.status).localeCompare(String(b.status));
    });
  },

  async save(job) {
    const db = await getDatabase();
    await db.put(JOB_STORE, job);
    return job;
  },

  async saveMany(jobs) {
    const db = await getDatabase();
    const tx = db.transaction(JOB_STORE, 'readwrite');
    await Promise.all(jobs.map((job) => tx.store.put(job)));
    await tx.done;
    return jobs;
  },

  async delete(id) {
    const db = await getDatabase();
    await db.delete(JOB_STORE, id);
  },

  async replaceAll(jobs) {
    const db = await getDatabase();
    const tx = db.transaction(JOB_STORE, 'readwrite');
    await tx.store.clear();
    await Promise.all(jobs.map((job) => tx.store.put(job)));
    await tx.done;
    return jobs;
  }
};
