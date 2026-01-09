# Journal de bord - TP Qualité Dev

## Décisions importantes

### Architecture
- **CQRS** : Séparation lecture/écriture avec services domain et read
- **Event Sourcing** : Events stockés dans event_log, projections via outbox
- **Microservices** : product-registry-domain-service, product-registry-read-service, store-back

### Choix techniques
- **Framework** : Quarkus
- **Base de données** : PostgreSQL avec Liquibase pour les migrations
- **Tests** : JUnit 5 + RestAssured pour les tests d'intégration

---

## Réponses aux questions du TP

### Exercice 1
- Voir [Exercice1.md](../../Exercice1.md)

### Exercice 2
- Voir [Exercice2.md](../../Exercice2.md)

### Exercice 3
- Travail individuel - pas d'équipe
- Tag v0.1.0 créé

### Exercice 4
- À compléter

---

## Historique des modifications

| Date | Description |
|------|-------------|
| 2026-01-09 | Création du journal de bord |
| 2026-01-09 | Complétion des Javadoc (Exercice 2) |
| 2026-01-09 | Tag v0.1.0 créé |
