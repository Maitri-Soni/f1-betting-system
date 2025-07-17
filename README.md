# 🏁 Formula 1 Betting Backend API (Spring Boot)

This is a Java Spring Boot REST API that simulates a Formula 1 betting platform. It integrates with the open-source [https://openf1.org](https://openf1.org) API to fetch real event and driver data, allows users to place bets, and resolves outcomes.

---

## ✅ Features

- View upcoming or past F1 events (filterable by year, session type, country)
- Randomized betting odds for each driver (2, 3, or 4)
- Place a single bet on a driver in any session
- Simulate event outcome and automatically update bets
- Balance system for users (initial balance = 100 EUR)

---

## 🛠 Technologies

- Java 17+
- Spring Boot
- Spring Data JPA
- H2 in-memory DB (for quick setup)
- Lombok
- Jackson
- RestTemplate

---

## ▶️ Running the App

### Requirements:
- Java 17+
- Maven

### Steps:

```bash
# 1. Clone the repo
git clone https://github.com/Maitri-Soni/f1-betting-system.git
cd f1-betting-app

# 2. Build and run
mvn spring-boot:run
```

## H2 Console
- Access H2 Console at: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:f1bettingdb

---

###	API Usage:
1. List Events (GET /api/events)
```
	curl --location 'http://localhost:8080/api/events?year=2025&sessionType=Race&country=Canada'
```
2. Place a Bet (POST /api/bets)
```
	curl --location 'http://localhost:8080/api/bets' \
	--header 'Content-Type: application/json' \
	--data '{
		  "userId": 1,
		  "sessionId": "9963",
		  "driverId": "22",
		  "amount": 10.0
		}'
```
	
3. Submit Event Outcome (POST /api/event-outcome)
```
	curl --location 'http://localhost:8080/api/event-outcome' \
	--header 'Content-Type: application/json' \
	--data '{
		  "sessionId": "9963",
		  "winningDriverId": "22"
		}'
```
