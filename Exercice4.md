
# TP Exercice 4 : Projection des Ã©vÃ©nements dans des vues matÃ©rialisÃ©es

## TÃ¢che 1 : Questions sur la base de code

- **Interface Projector :** applique un Ã©vÃ©nement Ã  une vue matÃ©rialisÃ©e et met Ã  jour son Ã©tat.

- **Type S :** reprÃ©sente l'Ã©tat de la vue matÃ©rialisÃ©e (DTO ou entitÃ©).

- **Javadoc pour S :** S est le modÃ¨le de lecture mis Ã  jour par les Ã©vÃ©nements.

- **Pourquoi interface Projector :** permet plusieurs implÃ©mentations, facilite les tests et le dÃ©couplage.

- **RÃ´le de ProjectionResult :** contient le rÃ©sultat dâun Ã©vÃ©nement (succÃ¨s, Ã©chec, nouvel Ã©tat).

- **IntÃ©rÃªt de la monade :** gÃ¨re succÃ¨s/erreur sans exceptions, facilite la composition et les tests.

## TÃ¢che 2 : Outboxing

- **OutboxRepository :** stocke les Ã©vÃ©nements Ã  publier et suit leur Ã©tat (PENDING, SENT, FAILED).

- **Garantie de livraison :** Ã©crire lâÃ©tat mÃ©tier + outbox dans la mÃªme transaction ; le dispatcher lit ensuite et publie.

**Fonctionnement concret :**

- Commande -> Ã©criture entitÃ©s + insert outbox dans la mÃªme transaction.

- Transaction commitÃ©e.

- Dispatcher lit outbox PENDING -> publie au broker â update status ou retry.

- **Gestion des erreurs :** retries, backoff, marquage FAILED, Ã©ventuellement dead-letter table.

**Diagramme simplifiÃ© :**

```
Service -> DB (domain + outbox)
Outbox Dispatcher -> DB.outbox -> Broker -> Consumers
```

## TÃ¢che 3 : Journal d'Ã©vÃ©nements

- **RÃ´le :** archive append-only pour audit, replay et traÃ§abilitÃ©.

- **EventLogRepository.append :** seule mÃ©thode pour garantir lâimmuabilitÃ©.

- **Implications :**

	- On peut reconstruire les projections par replay.

	- Pas de suppression -> audit simple mais nÃ©cessitÃ© de snapshots.

	- Autres usages : audit, rapports, debugging, analytics.

## TÃ¢che 4 : Limites de CQRS

- **Limites :** complexitÃ©, cohÃ©rence Ã©ventuelle, duplication de donnÃ©es, versioning des Ã©vÃ©nements.

- **Limites compensÃ©es :** Outbox -> fiabilitÃ©, EventLog -> replay des projections.

- **Nouvelles limites :** synchronisation de plusieurs projections, tests plus nombreux, versioning.

- **Projections multiples :** un Ã©vÃ©nement peut dÃ©clencher plusieurs projections, pas dâatomicitÃ© cross-projections -> incohÃ©rences temporaires.
- **Solutions :** idempotence, versioning, Sagas/orchestrateurs, monitoring, snapshots et compactage. 