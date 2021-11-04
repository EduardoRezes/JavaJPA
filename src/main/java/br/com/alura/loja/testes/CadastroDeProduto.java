package br.com.alura.loja.testes;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {
		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		
		Produto p = produtoDao.buscarPorID(1l);
		System.out.println(p.getPreco());
		
		List<Produto> todos = produtoDao.buscarPorNome("Xiaomi Redmi");
		todos.forEach( p2 -> System.out.println(p.getNome()));
	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		//Produto celular = new Produto("Xiaomu Redmi", "4GB RAM - 128 GB", new BigDecimal("800"), celulares);
		
		EntityManager em = JPAUtil.getEntityManager();
		//ProdutoDao produtoDao = new ProdutoDao(em);
		//CategoriaDao categoriaDao = new CategoriaDao(em);
		
		em.persist(celulares);
		celulares.setNome("XPTO");
		
		//em.flush();
		em.clear();
		celulares = em.merge(celulares);
		celulares.setNome("1234");
		//em.flush();
		
		/*em.getTransaction().begin();
		//categoriaDao.cadastrar(celulares);
		//produtoDao.cadastrar(celular);
		//em.getTransaction().commit();
		  em.close();*/
	}
}
