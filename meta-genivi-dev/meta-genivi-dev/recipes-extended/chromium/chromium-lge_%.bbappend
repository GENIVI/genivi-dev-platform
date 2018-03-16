# FIXME: workaround for https://at.projects.genivi.org/jira/browse/LM-2
CHROMIUM_EXTRA_ARGS_append = " --window-size=1728,1080"

# Required to run Chromium as root. Not recommended for production.
CHROMIUM_EXTRA_ARGS_append = " --no-sandbox"