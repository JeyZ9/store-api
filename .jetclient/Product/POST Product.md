```toml
name = 'POST Product'
method = 'POST'
url = 'http://localhost:8080/api/v1/products'
sortWeight = 2000000
id = '8c164075-3ba9-4f75-af96-d725fdcbced1'

[[headers]]
key = 'Content-Type'
value = 'application/json'

[body]
type = 'JSON'
raw = '''
{
  "productName": "iPhone 5s Plus",
  "productPrice": 4590,
  "productQuantity": 1
}'''
```
