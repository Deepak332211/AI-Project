export default function Header({
  search,
  onSearchChange,
  sortOrder,
  onSortOrderChange,
  theme,
  onThemeToggle,
  onAddJob,
  onExport,
  onImportClick
}) {
  return (
    <header className="border-b border-slate-200 bg-white/90 px-4 py-4 backdrop-blur dark:border-slate-800 dark:bg-slate-950/90 sm:px-6">
      <div className="mx-auto flex max-w-[1800px] flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
        <div>
          <h1 className="text-2xl font-semibold tracking-normal text-slate-950 dark:text-white">
            Job Tracker
          </h1>
          <p className="mt-1 text-sm text-slate-500 dark:text-slate-400">
            Local-first Kanban for private, offline-friendly job search tracking.
          </p>
        </div>

        <div className="flex flex-col gap-3 md:flex-row md:items-center">
          <input
            value={search}
            onChange={(event) => onSearchChange(event.target.value)}
            placeholder="Search company or role"
            className="h-10 w-full rounded-md border border-slate-200 bg-white px-3 text-sm text-slate-900 outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-100 dark:border-slate-800 dark:bg-slate-900 dark:text-white dark:focus:ring-blue-950 md:w-64"
          />

          <select
            value={sortOrder}
            onChange={(event) => onSortOrderChange(event.target.value)}
            className="h-10 rounded-md border border-slate-200 bg-white px-3 text-sm text-slate-800 outline-none transition focus:border-blue-500 focus:ring-2 focus:ring-blue-100 dark:border-slate-800 dark:bg-slate-900 dark:text-slate-100 dark:focus:ring-blue-950"
          >
            <option value="manual">Manual order</option>
            <option value="newest">Newest first</option>
            <option value="oldest">Oldest first</option>
          </select>

          <div className="flex flex-wrap items-center gap-2">
            <button
              type="button"
              onClick={onThemeToggle}
              className="h-10 rounded-md border border-slate-200 px-3 text-sm font-medium text-slate-700 transition hover:bg-slate-100 dark:border-slate-800 dark:text-slate-200 dark:hover:bg-slate-900"
            >
              {theme === 'dark' ? 'Light' : 'Dark'}
            </button>
            <button
              type="button"
              onClick={onImportClick}
              className="h-10 rounded-md border border-slate-200 px-3 text-sm font-medium text-slate-700 transition hover:bg-slate-100 dark:border-slate-800 dark:text-slate-200 dark:hover:bg-slate-900"
            >
              Import
            </button>
            <button
              type="button"
              onClick={onExport}
              className="h-10 rounded-md border border-slate-200 px-3 text-sm font-medium text-slate-700 transition hover:bg-slate-100 dark:border-slate-800 dark:text-slate-200 dark:hover:bg-slate-900"
            >
              Export
            </button>
            <button
              type="button"
              onClick={onAddJob}
              className="h-10 rounded-md bg-slate-950 px-4 text-sm font-semibold text-white shadow-sm transition hover:bg-slate-800 dark:bg-white dark:text-slate-950 dark:hover:bg-slate-200"
            >
              Add job
            </button>
          </div>
        </div>
      </div>
    </header>
  );
}
