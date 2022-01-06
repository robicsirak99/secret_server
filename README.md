Az API egy Spring keretrendszerben készült titok tároló és megosztó.
Bárki létrehozhat és elmenthet egy titkot az APIn keresztül.
A szerver generál egy random hash értéket a titokhoz, csak ezzel lehet a későbbiekben újra hozzáférni a titokhoz.
A titok adott idő, vagy adott megtekintés után lejár és már nem lesz elérhető. Ezeket a paramétereket a felhasználó adja meg a titok létrehozásakor.

-mysql adatbázist használ, amelyben a beállításai az 'src > main > resources > application.properties' útvonalon található fáljban módosítható
