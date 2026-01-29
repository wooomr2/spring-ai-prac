-- PG vector docker
````
docker run `
  -dit --rm `
  --name postgres `
  -p 5432:5432 `
  -e POSTGRES_USER=postgres `
  -e POSTGRES_PASSWORD=postgres `
  pgvector/pgvector:pg18-trixie

docker exec -it postgres psql -U postgres
