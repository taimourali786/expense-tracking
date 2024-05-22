# HELPDESK BACKEND
Backend for helpdesk

Requirements:
1. Docker

Database Setup:
1. Start Postgres Container
   - docker run -p5432:5432 -d --name postgresdb -e POSTGRES_PASSWORD=admin -v 
     $HOME/pgdata:/var/lib/postgresql/data postgres
2. Connect to database
   - psql postgresql://postgres:admin@localhost:5432/postgres 
3. Run following SQL commands to setup schema and user
   - CREATE SCHEMA expense_tracking;
   - CREATE USER "expense-tracking";
   - ALTER USER "expense-tracking" WITH password 'admin';
   - GRANT CREATE, USAGE ON SCHEMA expense_tracking to "expense-tracking";
   - GRANT ALL ON ALL TABLES IN SCHEMA expense_tracking to "expense-tracking";


