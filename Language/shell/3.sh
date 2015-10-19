#!/bin/bash

X=3
Y=4
empty_string=""
if [[ $X -lt $Y ]]; then
    echo "$X=${X}, which is smaller than $Y=${Y}"
fi

if [[ -n "$empty_string" ]]; then
    echo "empty string is non_empty"
fi

if [[ -e "${HOME}/.fvwmrc" ]]; then
    echo "you have a .fvwmrc file"
    if [[ -L "${HOME}/.fvwmrc" ]]; then
        echo "it's a symbolic link"
    elif [[ -f "${HOME}/.fvwmrc" ]]; then
        echo "it's a regular file"
    fi
else
    echo "you have no .fvwmrc file"
fi

