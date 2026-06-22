import { openDB } from 'idb';

export const DB_NAME = 'local-first-job-tracker';
export const DB_VERSION = 1;
export const JOB_STORE = 'jobs';

export const getDatabase = () =>
  openDB(DB_NAME, DB_VERSION, {
    upgrade(database) {
      if (!database.objectStoreNames.contains(JOB_STORE)) {
        const store = database.createObjectStore(JOB_STORE, { keyPath: 'id' });
        store.createIndex('status', 'status');
        store.createIndex('appliedDate', 'appliedDate');
        store.createIndex('order', 'order');
      }
    }
  });
