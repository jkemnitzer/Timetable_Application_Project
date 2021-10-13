# Gitflow Regeln

Dieses Repository wird anhand der allgemein bekannten git-Workflow Regeln gepflegt. 
Das hat den großen Vorteil, dass eine gewisse Qualität des Produktivcodes gewährleistet wird und viele 
Entwickler gleichzeitig auf dem Repository arbeiten können, ohne sich gegenseitig zu behindern.

## Allgemeiner Ansatz

Der grundlegende Ansatz besteht in einem gesicherten Produktiv-Branch, in unserem Fall dem `main`-Branch. 
Dieser enthält nur geprüften und getesteten Code, weshalb er als stabil angesehen werden kann.

Die meiste Arbeit wird auf einem Arbeiter-Branch geleistet, hier der `development`-Branch. 
Dieser enthält immer den aktuellen Code, jedoch wird niemals direkt auf ihm gearbeitet.

Jede neue Funktion - so klein sie auch sein mag - wird auf einem `feature`- Branch erstellt.
Feature Branches werden vom aktuellen `HEAD` des `development`-Branches gebrancht. 
Jeder **muss** von einem Prüfer gegengecheckt und genehmigt werden, 
bevor er wieder in den `development`-Branch gemerged werden kann. 
Somit gilt das Feature als fertig implementiert.

Sobald eine Art stabile Version auf dem `development`-Branch erreicht wurde wird dieser Stand in einen `release`-Branch 
gemerged und als Release Kandidat getaggt und weiter getestet. 
Jeder im Test gefundene Fehler wird in sogenannten `bugfix`-Branches gefixt, getestet und
wieder in den `release` gemerged und als neuer Release-Kandidat getaggt.

![Gitflow Ansatz](https://zepel.io/blog/content/images/2020/05/GitFlow-git-workflow-2.png "Gitflow workflow with hotfix and release branches (zepel.io)")

Ist ein Release Kandidat gefunden, der als fertig anerkannt wird, wird der Commit mit dem höchsten Release-Kandidat-Tag 
in den `main` und zurück in den `development` gemerged und als neue Version getaggt. 

Im `main` gefundene Fehler werden in vom `main` gebranchten `hotfix`-Branches gefixt, 
getestet und wieder in den `main` und `development` gemerged, falls der Fix kritisch ist. 
Ansonsten wird er als Issue ins Backlog übernommen und dann mit den Features als `ìssue`-Branch auf dem `development`-
Branch gefixt.

## FAQ

### Worauf arbeitet man in der Regel?
Beim Anfangen eines neuen features brancht sich jeder Entwickler für seine Teilarbeit einen `feature`-Branch vom 
aktuellen `development`-Branch ab und benennt diesen in folgendem Muster auf **Englischer**-Sprache:

`feature/<feature-id>_<short_description>`, Bsp:

`feature/1234_development_of_login_button`

Man achte auf:
* Englisch, um allen Entwicklern das Mitarbeiten zu erleichtern
* Alles lowercase
* snake case

### Was mache ich, wenn ich fertig bin?
Wenn ein Feature fertig ist und getestet wurde, wird für den `feature`-Branch ein Pull-Request auf den `development`-
Branch erstellt. Wichtig ist hier, dass der `development`-Branch vorher in den eigenen `feature`-Branch gemerged wird, 
mögliche merge-Konflikte zu vermeiden. 

Nach Approval des Reviewers wird der `feature`-Branch in den `development`-Branch gemerged.

### Wie regelmäßig sollte ich committen?

So oft wie möglich, jedoch spätestens, wenn eine Teilaufgabe gelöst wurde. 
Das verhindert Codeverluste, macht kleine Änderungen besser rückkehrbar.
Commit Messages sollten ebenfalls Englisch und aussagekräftig sein!