# Local-First Job Tracker

A privacy-focused single-page Kanban app for tracking job applications entirely in the browser. It uses React 18, Vite, Tailwind CSS, IndexedDB through `idb`, and `@dnd-kit/core` for drag-and-drop.

## Features

- Local-only persistence with IndexedDB
- Add, edit, delete, move, and reorder jobs
- Kanban columns: Wishlist, Applied, Follow-up, Interview, Offer, Rejected
- Search by company or role
- Column counts and status colors
- LinkedIn posting link on cards
- Days since applied
- Form validation for required fields and URLs
- Dark and light mode
- JSON export and import backup
- Sort by manual order, newest, or oldest
- Responsive board with independent column scrolling

## Project Structure

```text
src/
  components/
    Header.jsx
    JobCard.jsx
    JobModal.jsx
    KanbanBoard.jsx
    KanbanColumn.jsx
  db/
    database.ts
    jobRepository.ts
  hooks/
    useJobs.js
    useLocalStorage.js
  pages/
    JobTrackerPage.jsx
  utils/
    date.js
    jobs.js
    statuses.js
  App.jsx
  main.jsx
  styles.css
```

## Data Model

```js
{
  id: string,
  companyName: string,
  jobTitle: string,
  linkedinUrl: string,
  resumeUsed: string,
  appliedDate: string,
  salaryRange: string,
  notes: string,
  status: 'wishlist' | 'applied' | 'followup' | 'interview' | 'offer' | 'rejected',
  order: number,
  createdAt: string,
  updatedAt: string
}
```

`order`, `createdAt`, and `updatedAt` are internal fields used for durable card ordering and audit-friendly local records.

## Local Development

```bash
npm install
npm run dev
```

Open the local Vite URL shown in the terminal, usually `http://localhost:5173`.

## Production Build

```bash
npm run build
npm run preview
```

The static production output is generated in `dist/`.

## Persistence Architecture

The app uses a repository pattern:

- `src/db/database.ts` opens and migrates the IndexedDB database.
- `src/db/jobRepository.ts` provides `getAll`, `save`, `saveMany`, `delete`, and `replaceAll`.
- `src/hooks/useJobs.js` owns UI state and calls the repository for every mutation.

There is no backend, authentication, API, or external database. Refreshing the browser restores jobs from IndexedDB.

## Backup and Restore

Use `Export` to download a JSON backup. Use `Import` to restore a backup. Import replaces the current local board after confirmation.

## GitHub Quick Setup

```bash
git init
git add .
git commit -m "Initial local-first job tracker"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/local-first-job-tracker.git
git push -u origin main
```

Keep the repository public if you want to deploy it from a free Vercel account without extra private repo restrictions.
