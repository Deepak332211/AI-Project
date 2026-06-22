import { useDroppable } from '@dnd-kit/core';
import JobCard from './JobCard.jsx';

export default function KanbanColumn({ status, jobs, onEdit, onDelete }) {
  const { setNodeRef, isOver } = useDroppable({
    id: status.id,
    data: {
      type: 'column',
      status: status.id
    }
  });

  return (
    <section className="flex h-[calc(100vh-220px)] min-h-[520px] w-[18rem] shrink-0 flex-col rounded-lg border border-slate-200 bg-slate-100/80 dark:border-slate-800 dark:bg-slate-900/70 xl:w-full">
      <div className="flex items-center justify-between border-b border-slate-200 px-3 py-3 dark:border-slate-800">
        <div className="flex items-center gap-2">
          <span className={`h-2.5 w-2.5 rounded-full ${status.accent}`} />
          <h2 className="text-sm font-semibold text-slate-900 dark:text-white">{status.label}</h2>
        </div>
        <span className={`rounded-full px-2 py-0.5 text-xs font-semibold ${status.soft}`}>
          {jobs.length}
        </span>
      </div>

      <div
        ref={setNodeRef}
        className={`flex-1 space-y-3 overflow-y-auto p-3 transition ${
          isOver ? 'bg-blue-50/80 dark:bg-blue-950/20' : ''
        }`}
      >
        {jobs.map((job) => (
          <JobCard
            key={job.id}
            job={job}
            statusMeta={status}
            onEdit={onEdit}
            onDelete={onDelete}
          />
        ))}

        {jobs.length === 0 ? (
          <div className="flex h-28 items-center justify-center rounded-lg border border-dashed border-slate-300 text-center text-sm text-slate-400 dark:border-slate-700 dark:text-slate-500">
            Drop jobs here
          </div>
        ) : null}
      </div>
    </section>
  );
}
