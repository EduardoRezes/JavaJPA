package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDao {
	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		this.em = em;
	}
	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}
	public void atualizar(Produto produto) {
		this.em.merge(produto);
	}
	public void remover(Produto produto) {
		this.em.remove(produto);
	}
	public Produto buscarPorID(Long id) {
		return em.find(Produto.class, id);
	}
	
	public List<Produto> buscarTodos(){
		return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
	}
	
	public List<Produto> buscarPorNome(String nome){
		String jpql = "SELECT p FROM Produto p WHERE p.nome = ?1";
		return em.createQuery(jpql, Produto.class).setParameter(1, nome).getResultList();
	}
	
	public List<Produto> buscarPorNomeDaCategoria(String nome){
		String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = ?1";
		return em.createQuery(jpql, Produto.class).setParameter(1, nome).getResultList();
	}
	
	public BigDecimal buscarPrecoDoProdutoComNome(String nome){
		String jpql = "SELECT p.preco FROM Produto p WHERE p.categoria.nome = ?1";
		return em.createQuery(jpql, BigDecimal.class).setParameter(1, nome).getSingleResult();
	}
	
	//Consulta por parametros opcionais
	public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro){
		/* Modo Gambiarra
		String jpql = "SELECT p FROM Produto p WHERE 1=1";
		if (nome != null && !nome.trim().isEmpty()) {
			jpql = "AND p.nome = :nome";
		}
		if (preco != null) {
			jpql = "AND p.preco = :preco";
		}
		if (dataCadastro != null) {
			jpql = "AND p.dataCadastro = :dataCadastro";
		}
		
		TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
		
		//setando os parametros na query
		if (nome != null && !nome.trim().isEmpty()) {
			query.setParameter("nome", nome);
		}
		if (preco != null) {
			query.setParameter("preco", preco);
		}
		if (dataCadastro != null) {
			query.setParameter("dataCadastro", dataCadastro);
		}
		
		return query.getResultList();*/
		
		/*Criteria Mode*/
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query =  builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class); // tendo o from atribuido a Classe, o API Criteria sabe que o SELECT vai ser da classe Produto, não precisando declarar o SELECT
		Predicate filtros = builder.and();
		
		if (nome != null && !nome.trim().isEmpty()) filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
		
		if (preco != null) filtros = 	builder.and(filtros, builder.equal(from.get("preco"), preco));
		
		if (dataCadastro != null) filtros =  builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
		query.where(filtros);
		return em.createQuery(query).getResultList();
	}
}
