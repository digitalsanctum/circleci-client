
# circleci-client

A CircleCI client for API version 1.1 (https://circleci.com/docs/api/v1-reference/)

Note: Most of the read-only operations are implemented but write operations are still pending. Pull requests welcome :)

## build

To test, the following environment variables are required:

```
HEROKU_API_KEY,
CIRCLECI_TEST_USER,
CIRCLECI_TEST_PROJECT,
CIRCLECI_TEST_VCS_TYPE,
CIRCLECI_TEST_FINGERPRINT
```

Then run:

```./gradlew clean build```
  

