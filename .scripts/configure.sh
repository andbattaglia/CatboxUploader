#!/bin/bash

function installHooks() {
    ln -sf ../../.hooks/post-checkout .git/hooks/post-checkout
    ln -sf ../../.hooks/post-merge .git/hooks/post-merge
    ln -sf ../../.hooks/pre-commit .git/hooks/pre-commit
}

installHooks