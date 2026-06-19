import { useEffect, useMemo, useRef, useState } from 'react';
import Header from '../components/Header.jsx';
import JobModal from '../components/JobModal.jsx';
import KanbanBoard from '../components/KanbanBoard.jsx';
import { useJobs } from '../hooks/useJobs';
import { useLocalStorage } from '../hooks/useLocalStorage';
import { STATUSES } from '../utils/statuses';

const sortJobs = (jobs, sortOrder) => {
  const copy = [...jobs];

  if (sortOrder === 'newest') {
    return copy.sort((a, b) => String(b.appliedDate).localeCompare(String(a.appliedDate)));
  }

  if (sortOrder === 'oldest') {
    return copy.sort((a, b) => String(a.appliedDate).localeCompare(String(b.appliedDate)));
  }

  return copy.sort((a, b) => (a.order ?? 0) - (b.order ?? 0));
};

export default function JobTrackerPage() {
  const { jobs, isLoading, error, saveJob, deleteJob, moveJob, importJobs } = useJobs();
  const [theme, setTheme] = useLocalStorage('job-tracker-theme', 'light');
  const [search, setSearch] = useState('');
  const [sortOrder, setSortOrder] = useState('manual');
  const [editingJob, setEditingJob] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const fileInputRef = useRef(null);

  useEffect(() => {
    document.documentElement.classList.toggle('dark', theme === 'dark');
  }, [theme]);

  const filteredJobsByStatus = useMemo(() => {
    const normalizedSearch = search.trim().toLowerCase();

    return STATUSES.reduce((groups, status) => {
      groups[status.id] = sortJobs(
        jobs.filter((job) => {
          const matchesStatus = job.status === status.id;
          const matchesSearch =
            !normalizedSearch ||
            job.companyName.toLowerCase().includes(normalizedSearch) ||
            job.jobTitle.toLowerCase().includes(normalizedSearch);

          return matchesStatus && matchesSearch;
        }),
        sortOrder
      );
      return groups;
    }, {});
  }, [jobs, search, sortOrder]);

  const openAddModal = () => {
    setEditingJob(null);
    setIsModalOpen(true);
  };

  const openEditModal = (job) => {
    setEditingJob(job);
    setIsModalOpen(true);
  };

  const handleSaveJob = async (job) => {
    await saveJob(job);
    setIsModalOpen(false);
    setEditingJob(null);
  };

  const handleDeleteJob = async (job) => {
    const confirmed = confirm(`Delete ${job.companyName} - ${job.jobTitle}?`);
    if (!confirmed) return;

    await deleteJob(job.id);
  };

  const handleExport = () => {
    const payload = {
      exportedAt: new Date().toISOString(),
      app: 'local-first-job-tracker',
      version: 1,
      jobs
    };
    const blob = new Blob([JSON.stringify(payload, null, 2)], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = `job-tracker-backup-${new Date().toISOString().slice(0, 10)}.json`;
    link.click();
    URL.revokeObjectURL(url);
  };

  const handleImport = (event) => {
    const file = event.target.files?.[0];
    event.target.value = '';
    if (!file) return;

    const reader = new FileReader();
    reader.onload = async () => {
      try {
        const parsed = JSON.parse(String(reader.result));
        const incomingJobs = Array.isArray(parsed) ? parsed : parsed.jobs;

        if (!Array.isArray(incomingJobs)) {
          throw new Error('Invalid backup format.');
        }

        const confirmed = confirm(
          `Import ${incomingJobs.length} jobs? This will replace the current local board.`
        );

        if (confirmed) {
          await importJobs(incomingJobs);
        }
      } catch (importError) {
        alert(importError.message || 'Unable to import backup.');
      }
    };
    reader.readAsText(file);
  };

  return (
    <div className="min-h-screen bg-slate-50 text-slate-900 dark:bg-slate-950 dark:text-slate-100">
      <Header
        search={search}
        onSearchChange={setSearch}
        sortOrder={sortOrder}
        onSortOrderChange={setSortOrder}
        theme={theme}
        onThemeToggle={() => setTheme(theme === 'dark' ? 'light' : 'dark')}
        onAddJob={openAddModal}
        onExport={handleExport}
        onImportClick={() => fileInputRef.current?.click()}
      />

      <input
        ref={fileInputRef}
        type="file"
        accept="application/json"
        onChange={handleImport}
        className="hidden"
      />

      {error ? (
        <div className="mx-4 mt-4 rounded-md border border-red-200 bg-red-50 px-4 py-3 text-sm font-medium text-red-700 dark:border-red-900 dark:bg-red-950 dark:text-red-200 sm:mx-6">
          {error}
        </div>
      ) : null}

      {isLoading ? (
        <main className="flex min-h-[60vh] items-center justify-center text-sm text-slate-500 dark:text-slate-400">
          Loading local board...
        </main>
      ) : (
        <main>
          <KanbanBoard
            jobsByStatus={filteredJobsByStatus}
            onMoveJob={moveJob}
            onEditJob={openEditModal}
            onDeleteJob={handleDeleteJob}
          />
        </main>
      )}

      <JobModal
        isOpen={isModalOpen}
        initialJob={editingJob}
        defaultStatus="wishlist"
        onClose={() => {
          setIsModalOpen(false);
          setEditingJob(null);
        }}
        onSave={handleSaveJob}
      />
    </div>
  );
}
