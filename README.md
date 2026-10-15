# Waterfall Particles — Fabric 26.3

Ein clientseitiger Fabric-Mod, der an vertikal fließendem Wasser kleine Wassertröpfchen/Spritzer erzeugt. Die Mod ist visuell: Sie ändert keine Wasser-Physik und benötigt keine Installation auf dem Server.

## Voraussetzungen

- Minecraft Java Edition 26.3
- Fabric Loader 0.19.5 oder neuer
- Fabric API für 26.3
- Java 25 (nur für den Build; Minecraft-Launcher verwaltet die Laufzeit je nach Installation)

## Build auf GitHub

1. Lade den **Inhalt** dieses Ordners in ein neues GitHub-Repository hoch.
2. Öffne **Actions** und wähle `Build Waterfall Particles (Minecraft 26.3)`.
3. Starte **Run workflow** oder pushe einen Commit auf `main`.
4. Lade beim abgeschlossenen Lauf das Artifact `WaterfallParticles-Fabric-26.3` herunter. Es enthält die fertige `.jar`.
5. Lege die JAR in den `mods`-Ordner deines Minecraft-Clients. Die Spieler, die den Effekt sehen wollen, müssen die Mod lokal installiert haben.

## Hinweis zum Quellstand

Die ursprüngliche vollständige Projekt-ZIP war in der zuletzt bereitgestellten Nachricht nicht enthalten; vorliegend waren das Build-Log und die korrigierte `CascadeParticle.java`. Dieses Projekt ist deshalb ein vollständiges, neu aufgebautes Fabric-26.3-Projekt anhand dieser Informationen und des gemeldeten API-Fehlers — keine bytegenaue Bearbeitung des ursprünglichen Repositories. Der GitHub-Workflow ist eingerichtet, aber ein Build kann in dieser Umgebung nicht lokal bestätigt werden.
