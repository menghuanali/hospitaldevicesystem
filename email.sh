#!/bin/sh

git filter-branch --env-filter '

OLD_EMAIL="1441471911@qq.com"
CORRECT_NAME="潘成花"
CORRECT_EMAIL="52o@qq52o.cn"

if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_COMMITTER_NAME="$CORRECT_NAME"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_AUTHOR_NAME="$CORRECT_NAME"
fi
' --tag-name-filter cat -- --branches --tags