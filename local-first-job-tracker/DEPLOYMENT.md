# Deployment Guide

This app is a static Vite site and deploys cleanly on GitHub and Vercel free plans.

## GitHub Repository

1. Create a new repository on GitHub named `local-first-job-tracker`.
2. Set visibility to `Public` for the simplest free-tier deployment path.
3. From this project folder, run:

```bash
git init
git add .
git commit -m "Initial local-first job tracker"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/local-first-job-tracker.git
git push -u origin main
```

If this folder already lives inside another Git repository, either push the parent repository or move this folder into its own standalone repository before running `git init`.

## Vercel Free-Tier Deployment

1. Sign in to Vercel with GitHub.
2. Choose `Add New` then `Project`.
3. Import the GitHub repository.
4. Use these settings:

```text
Framework Preset: Vite
Root Directory: local-first-job-tracker
Build Command: npm run build
Output Directory: dist
Install Command: npm install
```

5. Click `Deploy`.

Vercel will install dependencies, build the app, and serve the `dist/` output from its global CDN.

## Environment Variables

No environment variables are required. The application is fully client-side and stores data in the user's browser through IndexedDB.

## Production Notes

- IndexedDB data is scoped to the deployed domain and the user's browser profile.
- Vercel redeploys do not delete user data because data is not stored on Vercel.
- Users should export JSON backups before clearing browser data or changing domains.
- Browser private/incognito modes may clear IndexedDB after the session ends.

## Updating the App

After making changes:

```bash
git add .
git commit -m "Describe the update"
git push
```

Vercel automatically builds and deploys the new commit when the project is connected to GitHub.
