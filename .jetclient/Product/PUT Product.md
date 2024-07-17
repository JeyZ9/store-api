```toml
name = 'PUT Product'
method = 'PUT'
url = 'http://localhost:8080/api/v1/products/1'
sortWeight = 4000000
id = '9aa1285b-bb3f-4778-84b9-4afdd445af14'

[[headers]]
key = 'Content-Type'
value = 'application/json'

[body]
type = 'JSON'
raw = '''
{
  "productName": "iPhone 5s Plus",
  "productPrice": 4590,
  "productQuantity": 3
}'''
```
