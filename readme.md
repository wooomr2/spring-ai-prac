RAG: 내부 데이터, LLG이 학습할 때 사용한 데이터 외의 데이터 활용
Tool Calling: 외부 라이브러리를 이용, 실시간성이 필요한 것들

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
