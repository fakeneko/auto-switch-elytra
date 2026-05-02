Execute the release workflow for this project:
1. Get current branch name ($BRANCH), derive tag by stripping leading 'v' ($TAG = $BRANCH with 'v' removed)
2. Delete old tag locally and remotely: `git tag -d $TAG` and `git push origin :refs/tags/$TAG`
3. Stage and commit all changes: `git add -A` then `git commit -m "$MSG"` where $MSG is provided by the user
4. Create new tag: `git tag $TAG`
5. Push branch and tag: `git push origin $BRANCH` and `git push origin $TAG`
6. Report results at each step.

Run each step sequentially with Bash, reporting progress as you go.