export const todayISO = () => new Date().toISOString().slice(0, 10);

export const daysSince = (dateValue) => {
  if (!dateValue) return 0;

  const start = new Date(`${dateValue}T00:00:00`);
  const now = new Date();
  const diff = now.getTime() - start.getTime();
  return Math.max(0, Math.floor(diff / 86_400_000));
};

export const formatAppliedLabel = (dateValue) => {
  const days = daysSince(dateValue);

  if (days === 0) return 'Applied today';
  if (days === 1) return 'Applied 1 day ago';
  return `Applied ${days} days ago`;
};
