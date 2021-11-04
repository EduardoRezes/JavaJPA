package br.com.alura.loja.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private LocalDate dataCadastro = LocalDate.now();

	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL) // necessário mapear quando for relação bidirecional, passando o nome do atributo, caso não mapeie, a JPA vai criar uma outra tabela
	private List<ItemPedido> itens = new ArrayList<ItemPedido>();
	
	public Pedido() {}
	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void adicionarItem(ItemPedido item) {
		//Para evitar espalhamento de codigo, ao adicionar um item a lista de pedido, tambem adicionar um item na tabela relacionada a pedidos.
		item.setPedido(this);
		this.itens.add(item);
		this.valorTotal = this.valorTotal.add(item.getValor());
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
