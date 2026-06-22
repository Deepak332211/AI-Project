import { useEffect, useMemo, useState } from 'react';
import { createEmptyJob, validateJob } from '../utils/jobs';
import { STATUSES } from '../utils/statuses';

export default function JobModal({ isOpen, initialJob, defaultStatus, onClose, onSave }) {
  const emptyJob = useMemo(() => createEmptyJob(defaultStatus), [defaultStatus]);
  const [form, setForm] = useState(initialJob ?? emptyJob);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (isOpen) {
      setForm(initialJob ?? createEmptyJob(defaultStatus));
      setErrors({});
    }
  }, [defaultStatus, initialJob, isOpen]);

  if (!isOpen) return null;

  const updateField = (field, value) => {
    setForm((current) => ({ ...current, [field]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const nextErrors = validateJob(form);
    setErrors(nextErrors);

    if (Object.keys(nextErrors).length > 0) return;

    await onSave(form);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-950/50 p-4">
      <form
        onSubmit={handleSubmit}
        className="max-h-[92vh] w-full max-w-2xl overflow-y-auto rounded-lg border border-slate-200 bg-white shadow-2xl dark:border-slate-800 dark:bg-slate-950"
      >
        <div className="flex items-center justify-between border-b border-slate-200 px-5 py-4 dark:border-slate-800">
          <div>
            <h2 className="text-lg font-semibold text-slate-950 dark:text-white">
              {initialJob ? 'Edit job' : 'Add job'}
            </h2>
          </div>
          <button
            type="button"
            onClick={onClose}
            className="rounded-md px-3 py-1.5 text-sm font-semibold text-slate-500 transition hover:bg-slate-100 dark:text-slate-300 dark:hover:bg-slate-900"
          >
            Close
          </button>
        </div>

        <div className="grid gap-4 px-5 py-5 md:grid-cols-2">
          <Field label="Company Name" error={errors.companyName} required>
            <input
              value={form.companyName}
              onChange={(event) => updateField('companyName', event.target.value)}
              className="field-input"
            />
          </Field>

          <Field label="Job Title" error={errors.jobTitle} required>
            <input
              value={form.jobTitle}
              onChange={(event) => updateField('jobTitle', event.target.value)}
              className="field-input"
            />
          </Field>

          <Field label="LinkedIn URL" error={errors.linkedinUrl}>
            <input
              value={form.linkedinUrl}
              onChange={(event) => updateField('linkedinUrl', event.target.value)}
              placeholder="https://www.linkedin.com/jobs/view/..."
              className="field-input"
            />
          </Field>

          <Field label="Resume Used">
            <input
              value={form.resumeUsed}
              onChange={(event) => updateField('resumeUsed', event.target.value)}
              placeholder="SDE_Resume_v3"
              className="field-input"
            />
          </Field>

          <Field label="Applied Date" error={errors.appliedDate}>
            <input
              type="date"
              value={form.appliedDate}
              onChange={(event) => updateField('appliedDate', event.target.value)}
              className="field-input"
            />
          </Field>

          <Field label="Salary Range">
            <input
              value={form.salaryRange}
              onChange={(event) => updateField('salaryRange', event.target.value)}
              placeholder="$180K-$220K"
              className="field-input"
            />
          </Field>

          <Field label="Status">
            <select
              value={form.status}
              onChange={(event) => updateField('status', event.target.value)}
              className="field-input"
            >
              {STATUSES.map((status) => (
                <option key={status.id} value={status.id}>
                  {status.label}
                </option>
              ))}
            </select>
          </Field>

          <Field label="Notes" className="md:col-span-2">
            <textarea
              value={form.notes}
              onChange={(event) => updateField('notes', event.target.value)}
              rows={5}
              className="field-input resize-y"
            />
          </Field>
        </div>

        <div className="flex items-center justify-end gap-2 border-t border-slate-200 px-5 py-4 dark:border-slate-800">
          <button
            type="button"
            onClick={onClose}
            className="h-10 rounded-md border border-slate-200 px-4 text-sm font-semibold text-slate-700 transition hover:bg-slate-100 dark:border-slate-800 dark:text-slate-200 dark:hover:bg-slate-900"
          >
            Cancel
          </button>
          <button
            type="submit"
            className="h-10 rounded-md bg-slate-950 px-4 text-sm font-semibold text-white transition hover:bg-slate-800 dark:bg-white dark:text-slate-950 dark:hover:bg-slate-200"
          >
            Save job
          </button>
        </div>
      </form>
    </div>
  );
}

function Field({ label, error, required = false, className = '', children }) {
  return (
    <label className={`block ${className}`}>
      <span className="mb-1.5 block text-sm font-medium text-slate-700 dark:text-slate-200">
        {label}
        {required ? <span className="text-red-500"> *</span> : null}
      </span>
      {children}
      {error ? <span className="mt-1 block text-xs font-medium text-red-600">{error}</span> : null}
    </label>
  );
}
