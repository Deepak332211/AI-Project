export const STATUSES = [
  {
    id: 'wishlist',
    label: 'Wishlist',
    accent: 'bg-slate-500',
    soft: 'bg-slate-100 text-slate-700 dark:bg-slate-800 dark:text-slate-200'
  },
  {
    id: 'applied',
    label: 'Applied',
    accent: 'bg-blue-500',
    soft: 'bg-blue-100 text-blue-700 dark:bg-blue-950 dark:text-blue-200'
  },
  {
    id: 'followup',
    label: 'Follow-up',
    accent: 'bg-yellow-500',
    soft: 'bg-yellow-100 text-yellow-800 dark:bg-yellow-950 dark:text-yellow-200'
  },
  {
    id: 'interview',
    label: 'Interview',
    accent: 'bg-purple-500',
    soft: 'bg-purple-100 text-purple-700 dark:bg-purple-950 dark:text-purple-200'
  },
  {
    id: 'offer',
    label: 'Offer',
    accent: 'bg-green-500',
    soft: 'bg-green-100 text-green-700 dark:bg-green-950 dark:text-green-200'
  },
  {
    id: 'rejected',
    label: 'Rejected',
    accent: 'bg-red-500',
    soft: 'bg-red-100 text-red-700 dark:bg-red-950 dark:text-red-200'
  }
];

export const STATUS_IDS = STATUSES.map((status) => status.id);

export const getStatusMeta = (statusId) =>
  STATUSES.find((status) => status.id === statusId) ?? STATUSES[0];
