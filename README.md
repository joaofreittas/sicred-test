# sicred-test
Test Sicred API Rest maked with SpringBoot

## Banco de Dados MySQL
### Variáveis de Ambiente
```
$BD_URL (define a url de conexão para o banco mysql)
$BD_USER (define usuário do banco de dados)
$BD_PASSW (define a senha para o banco de dados)
```

## Rotas da API
``` 
GET: /operacao
```
``` 
GET: /operacao/{id}
```
``` 
GET: /operacao/search?searchCriteria={ urlEncoded }

- buscar operações que tenham valor igual a 1500.0
searchCriteriaJsonExample: [{"key":"valor","operation":"EQUAL","value":"1500.0"}]

- buscar operações que nome do produto esteja contido em 'Financiamento'
searchCriteriaJsonExample: [{"key":"produto","operation":"MATCH","value":"Financiamento"}]

urlEncoded: %5B%7B%22key%22%3A%22valor%22%2C%22operation%22%3A%22%3A%22%2C%22value%22%3A%221500.0%22%7D%5D
```
``` 
POST: /operacao
{
  "taxaJuros": 0.99,
  "valor": 20.00,
  "vencimento": "2022-08-22",
  "situacao": "PAGO",
  "produto": "Algum produto",
	"associado": {
    "documento": "12345678912",
    "conta": "123456"
  }
}
```
```
PUT: /operacao/{id}
{
  "taxaJuros": 0.99,
  "valor": 20.00,
  "vencimento": "2022-08-22",
  "situacao": "ABERTO",
  "produto": "Algum produto",
	"associado": {
    "documento": "12345678912",
    "conta": "123456"
  }
}
```
```
DELETE: /operacao/{id}
```
