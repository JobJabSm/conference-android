#!/usr/bin/env bash

# Travis build triggered on a forked repository
if [ "$TRAVIS_REPO_SLUG" != "systers/conference-android" ]; then
    echo "Not the original repo."
    exit 0
fi

# Travis build triggered by a PR
if [ "$TRAVIS_PULL_REQUEST" != "false" ]; then
    echo "Just a PR."
    exit 0
fi

# Get commit author name
COMMITTER_NAME=$(git log -1 --pretty=format:'%an')

# Code pushed to develop branch
if [ "$TRAVIS_BRANCH" == "develop" ]; then
    echo "Code pushed to develop branch. Updating apk."
    # Set username and email
    git config --global user.name "m-murad"
    git config --global user.email "murad.kuka@gmail.com"

    # Create a new git repo to save apk
    cd $HOME
    mkdir apk
    cd apk
    git init

    # Copy generated apk into the new repo
    cp $HOME/build/systers/conference-android/app/build/outputs/apk/app-debug.apk $HOME/apk/

    # Add and commit the new apk
    git add app-debug.apk
    git commit -m "Apk update: Travis build $TRAVIS_BUILD_NUMBER by $COMMITTER_NAME"

    # Rename the current branch
    git branch -m apk

    # Pushing the apk branch to the original repo
    git push https://m-murad:$GITHUB_API_KEY@github.com/systers/conference-android apk --force --quiet> /dev/null
    if [ $? -eq 0 ]; then
        echo "Apk push successful."
    else
        echo "Apk push failed."
    fi
fi