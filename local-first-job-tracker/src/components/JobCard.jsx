import { useDraggable, useDroppable } from '@dnd-kit/core';
import { formatAppliedLabel } from '../utils/date';

export default function JobCard({ job, statusMeta, onEdit, onDelete }) {
  const {
    attributes,
    listeners,
    setNodeRef: setDraggableNodeRef,
    transform,
    isDragging
  } = useDraggable({
    id: job.id,
    data: {
      type: 'job',
      job
    }
  });
  const { setNodeRef: setDroppableNodeRef } = useDroppable({
    id: job.id,
    data: {
      type: 'card',
      job
    }
  });

  const setNodeRef = (node) => {
    setDraggableNodeRef(node);
    setDroppableNodeRef(node);
  };

  const style = transform
    ? {
        transform: `translate3d(${transform.x}px, ${transform.y}px, 0)`,
        zIndex: 50
      }
    : undefined;

  return (
    <article
      ref={setNodeRef}
      style={style}
      className={`rounded-lg border border-slate-200 bg-white p-4 shadow-card transition dark:border-slate-800 dark:bg-slate-900 ${
        isDragging ? 'opacity-75 ring-2 ring-blue-400' : ''
      }`}
    >
      <div className="flex items-start justify-between gap-3">
        <button
          type="button"
          className="min-w-0 flex-1 cursor-grab text-left active:cursor-grabbing"
          aria-label={`Drag ${job.companyName} ${job.jobTitle}`}
          {...listeners}
          {...attributes}
        >
          <div className="flex items-center gap-2">
            <span className={`h-2.5 w-2.5 rounded-full ${statusMeta.accent}`} />
            <h3 className="truncate text-sm font-semibold text-slate-950 dark:text-white">
              {job.companyName}
            </h3>
          </div>
          <p className="mt-1 line-clamp-2 text-sm text-slate-600 dark:text-slate-300">
            {job.jobTitle}
          </p>
        </button>

        {job.linkedinUrl ? (
          <a
            href={job.linkedinUrl}
            target="_blank"
            rel="noreferrer"
            title="Open LinkedIn posting"
            className="flex h-8 w-8 shrink-0 items-center justify-center rounded-md bg-blue-50 text-sm font-bold text-blue-700 transition hover:bg-blue-100 dark:bg-blue-950 dark:text-blue-200"
          >
            in
          </a>
        ) : null}
      </div>

      <div className="mt-4 space-y-2 text-xs text-slate-500 dark:text-slate-400">
        {job.resumeUsed ? (
          <p>
            <span className="font-medium text-slate-700 dark:text-slate-200">Resume:</span>{' '}
            {job.resumeUsed}
          </p>
        ) : null}
        <p>{formatAppliedLabel(job.appliedDate)}</p>
        {job.salaryRange ? (
          <p>
            <span className="font-medium text-slate-700 dark:text-slate-200">Salary:</span>{' '}
            {job.salaryRange}
          </p>
        ) : null}
        {job.notes ? <p className="line-clamp-3 leading-5">{job.notes}</p> : null}
      </div>

      <div className="mt-4 flex items-center justify-between gap-2 border-t border-slate-100 pt-3 dark:border-slate-800">
        <button
          type="button"
          onClick={() => onEdit(job)}
          className="rounded-md px-2.5 py-1.5 text-xs font-semibold text-slate-600 transition hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-800"
        >
          Edit
        </button>
        <button
          type="button"
          onClick={() => onDelete(job)}
          className="rounded-md px-2.5 py-1.5 text-xs font-semibold text-red-600 transition hover:bg-red-50 dark:text-red-300 dark:hover:bg-red-950"
        >
          Delete
        </button>
      </div>
    </article>
  );
}
