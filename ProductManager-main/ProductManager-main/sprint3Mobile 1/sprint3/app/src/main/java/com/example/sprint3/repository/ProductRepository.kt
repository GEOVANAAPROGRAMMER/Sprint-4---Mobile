package com.example.sprint3.repository

import com.example.sprint3.api.RetrofitClient
import com.example.sprint3.models.Produto
import com.google.firebase.database.*

class ProductRepository {

    // Referências para Firebase e Retrofit
    private val firebaseDatabase = FirebaseDatabase.getInstance().reference
    private val retrofitApi = RetrofitClient.api
    private val productsNode = "products"

    // Adiciona produto no Firebase
    fun addProductToFirebase(product: Produto, onComplete: (Boolean, String?) -> Unit) {
        val productId = firebaseDatabase.child(productsNode).push().key
        if (productId != null) {
            // Define o ID gerado para o produto
            val productWithId = product.copy(id = productId.toLong())
            firebaseDatabase.child(productsNode).child(productId).setValue(productWithId)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onComplete(true, null)
                        } else {
                            onComplete(false, task.exception?.message)
                        }
                    }
        } else {
            onComplete(false, "Erro ao gerar o ID do produto")
        }
    }

    // Recupera produtos do Firebase
    fun getProductsFromFirebase(onDataReceived: (List<Produto>, String?) -> Unit) {
        firebaseDatabase.child(productsNode).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val products = mutableListOf<Produto>()
                for (snapshot in dataSnapshot.children) {
                    val product = snapshot.getValue(Produto::class.java)
                    if (product != null) products.add(product)
                }
                onDataReceived(products, null)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onDataReceived(emptyList(), databaseError.message)
            }
        })
    }

    // Atualiza um produto no Firebase
    fun updateProductInFirebase(productId: String, updatedProduct: Produto, onComplete: (Boolean, String?) -> Unit) {
        firebaseDatabase.child(productsNode).child(productId).setValue(updatedProduct)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onComplete(true, null)
                    } else {
                        onComplete(false, task.exception?.message)
                    }
                }
    }

    // Deleta um produto no Firebase
    fun deleteProductFromFirebase(productId: String, onComplete: (Boolean, String?) -> Unit) {
        firebaseDatabase.child(productsNode).child(productId).removeValue()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onComplete(true, null)
                    } else {
                        onComplete(false, task.exception?.message)
                    }
                }
    }

    // Adiciona produto usando Retrofit (API)
    fun addProductToApi(product: Produto, onComplete: (Boolean, String?) -> Unit) {
        retrofitApi.addProduct(product).enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, "Erro ao adicionar produto via API")
                }
            }

            override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                onComplete(false, t.message)
            }
        })
    }

    // Recupera produtos da API via Retrofit
    fun getProductsFromApi(onDataReceived: (List<Produto>, String?) -> Unit) {
        retrofitApi.getProducts().enqueue(object : retrofit2.Callback<List<Produto>> {
            override fun onResponse(call: retrofit2.Call<List<Produto>>, response: retrofit2.Response<List<Produto>>) {
                if (response.isSuccessful) {
                    val products = response.body() ?: emptyList()
                    onDataReceived(products, null)
                } else {
                    onDataReceived(emptyList(), "Erro ao recuperar produtos via API")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Produto>>, t: Throwable) {
                onDataReceived(emptyList(), t.message)
            }
        })
    }

    // Atualiza um produto via API
    fun updateProductInApi(productId: Long, updatedProduct: Produto, onComplete: (Boolean, String?) -> Unit) {
        retrofitApi.updateProduct(productId, updatedProduct).enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, "Erro ao atualizar produto via API")
                }
            }

            override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                onComplete(false, t.message)
            }
        })
    }

    // Deleta um produto via API
    fun deleteProductFromApi(productId: Long, onComplete: (Boolean, String?) -> Unit) {
        retrofitApi.deleteProduct(productId).enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                if (response.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, "Erro ao deletar produto via API")
                }
            }

            override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                onComplete(false, t.message)
            }
        })
    }
}
