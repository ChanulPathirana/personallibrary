# Personal Library

A Spring Boot–based personal library backend for managing books, PDFs, papers, and notes, with PostgreSQL persistence and Google Drive integration for PDF uploads.

The backend is containerized with Docker, deployed on Render, uses Supabase PostgreSQL, and includes a GitHub Actions CI/CD pipeline.

## Live Backend

**API Base URL**

```text
https://personallibrary-6y1r.onrender.com
```

> The Render free instance may spin down after inactivity, so the first request can take a short time while the service wakes up.

## Repository

```text
https://github.com/ChanulPathirana/personallibrary.git
```

## Features

- Create, view, update, and delete library items
- Support for item types: Book, PDF, Paper, and Note
- Reading status tracking: To Read, Reading, and Completed
- Search library items by title
- Filter by item type and reading status
- Pagination and sorting
- Upload PDFs directly to Google Drive
- Store Google Drive file ID and URL in PostgreSQL
- Google OAuth connection flow
- Google Drive connection status and disconnect endpoints
- Dockerized deployment
- GitHub Actions CI/CD
- Managed PostgreSQL with Supabase

## Tech Stack

### Backend
- Java 25
- Spring Boot 4.1.1
- Spring MVC
- Spring Data JPA
- Hibernate
- Maven
- Jakarta Bean Validation

### Database
- PostgreSQL
- Supabase PostgreSQL in production

### External Integration
- Google OAuth 2.0
- Google Drive API

### Deployment
- Docker
- Render
- GitHub Actions

## Architecture

```text
Client / Frontend
       |
       v
Spring Boot REST API
       |
       +----------------------+
       |                      |
       v                      v
Supabase PostgreSQL     Google OAuth / Drive API
       |
       v
Library metadata
```

PDF files are uploaded to Google Drive. The database stores metadata, the Google Drive file ID, and the Google Drive URL.

## Project Structure

```text
src/
├── main/
│   ├── java/com/chanul/personallibrary/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   └── resources/
│       └── application.properties
│
└── test/
    └── java/com/chanul/personallibrary/
```

## Main Domain Model

A library item contains:

```text
id
title
author
type
status
googleDriveFileId
googleDriveUrl
```

### Item Types

```text
BOOK
PDF
PAPER
NOTE
```

### Reading Statuses

```text
TO_READ
READING
COMPLETED
```

## API Endpoints

### Library

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/library` | Create a library item |
| `GET` | `/api/library` | List items with pagination and sorting |
| `GET` | `/api/library/{id}` | Get an item by ID |
| `PUT` | `/api/library/{id}` | Update an item |
| `DELETE` | `/api/library/{id}` | Delete an item |
| `GET` | `/api/library/status/{status}` | Filter by reading status |
| `GET` | `/api/library/title/{title}` | Search by title |
| `GET` | `/api/library/type/{type}` | Filter by item type |
| `POST` | `/api/library/upload` | Upload a PDF to Google Drive and save metadata |

### Google Drive

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/google-drive/connect` | Start Google OAuth flow |
| `GET` | `/api/google-drive/callback` | Handle Google OAuth callback |
| `GET` | `/api/google-drive/status` | Check whether Google Drive is connected |
| `POST` | `/api/google-drive/disconnect` | Disconnect the current Google Drive connection |

## Example Requests

### List Library Items

```bash
curl "https://personallibrary-6y1r.onrender.com/api/library?page=0&size=5"
```

### Check Google Drive Status

```bash
curl https://personallibrary-6y1r.onrender.com/api/google-drive/status
```

### Upload a PDF

```bash
curl -X POST \
  https://personallibrary-6y1r.onrender.com/api/library/upload \
  -F "title=Clean Code" \
  -F "author=Robert C. Martin" \
  -F "type=PDF" \
  -F "status=TO_READ" \
  -F "file=@/path/to/file.pdf"
```

## Environment Variables

The application expects:

```text
DB_URL
DB_USERNAME
DB_PASSWORD

GOOGLE_CLIENT_ID
GOOGLE_CLIENT_SECRET
GOOGLE_REDIRECT_URI

PORT
```

Example local setup:

```text
DB_URL=jdbc:postgresql://localhost:5432/personallibrary
DB_USERNAME=library_user
DB_PASSWORD=your_password

GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
GOOGLE_REDIRECT_URI=http://localhost:8080/api/google-drive/callback
```

Do not commit real credentials. The `.env` file is excluded from version control.

## Local Development

### Requirements

- Java 25
- PostgreSQL
- Maven wrapper included in the project

Clone the repository:

```bash
git clone https://github.com/ChanulPathirana/personallibrary.git
cd personallibrary
```

Load local environment variables:

```bash
set -a
source .env
set +a
```

Run the application:

```bash
./mvnw spring-boot:run
```

The backend will be available at:

```text
http://localhost:8080
```

## Testing

Run all tests:

```bash
./mvnw clean test
```

Current verified result:

```text
Tests run: 5
Failures: 0
Errors: 0
Skipped: 0
```

Build the application:

```bash
./mvnw clean package
```

Generated executable JAR:

```text
target/personallibrary-0.0.1-SNAPSHOT.jar
```

## Docker

Build the image:

```bash
docker build -t personallibrary-backend .
```

Run locally:

```bash
docker run --rm \
  -p 8080:8080 \
  -e PORT=8080 \
  -e DB_URL="jdbc:postgresql://YOUR_DB_HOST:5432/YOUR_DB_NAME" \
  -e DB_USERNAME="YOUR_DB_USERNAME" \
  -e DB_PASSWORD="YOUR_DB_PASSWORD" \
  -e GOOGLE_CLIENT_ID="YOUR_GOOGLE_CLIENT_ID" \
  -e GOOGLE_CLIENT_SECRET="YOUR_GOOGLE_CLIENT_SECRET" \
  -e GOOGLE_REDIRECT_URI="http://localhost:8080/api/google-drive/callback" \
  personallibrary-backend
```

## Production Deployment

The production backend runs as a Dockerized Render Web Service.

```text
GitHub
   |
   v
GitHub Actions
   |
   | tests + package + Docker build verification
   v
Render Deploy Hook
   |
   v
Render Docker Web Service
   |
   +------> Supabase PostgreSQL
   |
   +------> Google Drive API
```

### Production Google OAuth Callback

```text
https://personallibrary-6y1r.onrender.com/api/google-drive/callback
```

The same URI must be configured as an authorized redirect URI in the Google Cloud OAuth client.

## CI/CD

GitHub Actions handles CI/CD.

For pull requests targeting `main`:

```text
Pull Request
   |
   v
Run tests
   |
   v
Build application
   |
   v
Build Docker image
```

For pushes to `main`:

```text
Push to main
   |
   v
Run tests
   |
   v
Build JAR
   |
   v
Verify Docker image
   |
   v
Trigger Render Deploy Hook
   |
   v
Deploy production backend
```

Production deployment occurs only after CI succeeds.

The Render Deploy Hook is stored as the GitHub Actions secret:

```text
RENDER_DEPLOY_HOOK_URL
```

The hook URL itself is not committed to the repository.

## Current Deployment Status

- Spring Boot backend deployed successfully on Render
- Docker deployment working
- Supabase PostgreSQL connection working
- Library API responding successfully
- Google Drive status endpoint responding
- CI/CD workflow configured with GitHub Actions and a Render Deploy Hook

## Planned Next Step

Frontend integration using:

- React
- TypeScript
- Vite
- Tailwind CSS
- React Router

The frontend will consume this backend API and be deployed separately.

## Security Notes

- Secrets are provided through environment variables
- `.env` is ignored by Git
- Production database credentials are stored in Render environment settings
- Google OAuth secrets are not stored in source code
- Google refresh token persistence allows the connection to survive application restarts
- The current application supports a single active Google Drive connection

## License

This project is currently intended for personal and educational use.
