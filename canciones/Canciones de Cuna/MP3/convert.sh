#!/bin/bash
set -x
for f in *.mp3; do
	ffmpeg -i "$f" -codec:a libmp3lame -qscale:a 5 "../$f"
done
