NEW_TAG="${new_tag}"
LAST_TAG="${latest_tag_version}"

if [ "$NEW_TAG" = "$LAST_TAG" ]; then
  echo "Error: New tag version ($NEW_TAG) is the same as the last tag version ($LAST_TAG)."
  exit 1
else
  echo "New tag version ($NEW_TAG) is different from the last tag version ($LAST_TAG)."
fi
