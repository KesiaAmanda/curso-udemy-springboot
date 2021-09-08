package com.loja.curso.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.loja.curso.domain.Categoria;
import com.loja.curso.domain.Cidade;
import com.loja.curso.domain.Cliente;
import com.loja.curso.domain.Endereco;
import com.loja.curso.domain.Estado;
import com.loja.curso.domain.ItemPedido;
import com.loja.curso.domain.Pagamento;
import com.loja.curso.domain.PagamentoComBoleto;
import com.loja.curso.domain.PagamentoComCartao;
import com.loja.curso.domain.Pedido;
import com.loja.curso.domain.Produto;
import com.loja.curso.domain.enums.EstadoPagamento;
import com.loja.curso.domain.enums.Perfil;
import com.loja.curso.domain.enums.TipoCliente;
import com.loja.curso.repositories.CategoriaRepository;
import com.loja.curso.repositories.CidadeRepository;
import com.loja.curso.repositories.ClienteRepository;
import com.loja.curso.repositories.EnderecoRepository;
import com.loja.curso.repositories.EstadoRepository;
import com.loja.curso.repositories.ItemPedidoRepository;
import com.loja.curso.repositories.PagamentoRepository;
import com.loja.curso.repositories.PedidoRepository;
import com.loja.curso.repositories.ProdutoRepository;

@Service
public class DBService {
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public void instantiateTestDatabase() throws ParseException {
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		Categoria categoria3 = new Categoria(null, "Eletrônicos");
		Categoria categoria4 = new Categoria(null, "Eletrônicos");
		Categoria categoria5 = new Categoria(null, "Decoração");
		Categoria categoria6 = new Categoria(null, "Jardinagem");
		Categoria categoria7 = new Categoria(null, "Escritório");
		Categoria categoria8 = new Categoria(null, "teste");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV", 1200.00);
		Produto p8 = new Produto(null, "Raçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		
		
		categoria1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		categoria2.getProdutos().addAll(Arrays.asList(p2, p3));
		categoria3.getProdutos().addAll(Arrays.asList(p5, p6));
		categoria4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		categoria5.getProdutos().addAll(Arrays.asList(p8));
		categoria6.getProdutos().addAll(Arrays.asList(p9, p10));
		categoria7.getProdutos().addAll(Arrays.asList(p11));
		
		p1.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
		p2.getCategorias().addAll(Arrays.asList(categoria1, categoria2, categoria4));
		p3.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
		p4.getCategorias().addAll(Arrays.asList(categoria2));
		p5.getCategorias().addAll(Arrays.asList(categoria3));
		p6.getCategorias().addAll(Arrays.asList(categoria3));
		p7.getCategorias().addAll(Arrays.asList(categoria4));
		p8.getCategorias().addAll(Arrays.asList(categoria5));
		p9.getCategorias().addAll(Arrays.asList(categoria6));
		p10.getCategorias().addAll(Arrays.asList(categoria6));
		p11.getCategorias().addAll(Arrays.asList(categoria7));
		
		categoriaRepository.saveAll(Arrays.asList(categoria1,categoria2, categoria3, categoria4, categoria5, categoria6, categoria7, categoria8));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");

		Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		estadoRepository.saveAll(Arrays.asList(estado1,estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1,cidade2,cidade3));
	
		Cliente cliente = new Cliente(null, "Maria Silva", "kesiaamandaladeia@gmail.com", "41141141141", TipoCliente.PESSOAFISICA, encoder.encode("123"));
		Cliente cliente2 = new Cliente(null, "Mara Silva", "kesia@gmail.com", "41141141143", TipoCliente.PESSOAFISICA, encoder.encode("123"));
		cliente2.addPerfil(Perfil.ADMIN);
		cliente2.getTelefones().addAll(Arrays.asList("27363627","938388383"));
		cliente.getTelefones().addAll(Arrays.asList("27363627","938388383"));
		
		Endereco endereco1 = new Endereco(null, "Rua", "300", "Apto 404", "Jardim", "38220834", cidade1, cliente);
		Endereco endereco2 = new Endereco(null, "Avenida", "105", "Sala 404", "Centro", "38220834", cidade2, cliente);
		Endereco endereco3 = new Endereco(null, "Travessa", "105", "Sala 404", "Centro", "38220834", cidade2, cliente);
		
		cliente.getEnderecos().addAll(Arrays.asList(endereco1,endereco2));
		cliente2.getEnderecos().addAll(Arrays.asList(endereco3));
		
		
		clienteRepository.saveAll(Arrays.asList(cliente,cliente2));
		enderecoRepository.saveAll(Arrays.asList(endereco1,endereco2,endereco3));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido pedido1 = new Pedido(null, dateFormat.parse("30/09/2021 10:31"), cliente, endereco1);
		Pedido pedido2 = new Pedido(null, dateFormat.parse("10/10/2021 19:35"), cliente, endereco2);
		
		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);
		
		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, dateFormat.parse("20/10/2021 00:00"), null);
		pedido2.setPagamento(pagamento2);
		
		cliente.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
		
		ItemPedido itemPedido1 = new ItemPedido(pedido1, p1, 0.00, 1, 2000.00);
		ItemPedido itemPedido2 = new ItemPedido(pedido1, p3, 0.00, 2, 80.00);
		ItemPedido itemPedido3 = new ItemPedido(pedido2, p2, 100.00, 1, 800.00);
		
		pedido1.getItemPedidos().addAll(Arrays.asList(itemPedido1, itemPedido2));
		pedido2.getItemPedidos().addAll(Arrays.asList(itemPedido3));
		
		p1.getItemPedidos().addAll(Arrays.asList(itemPedido1));
		p2.getItemPedidos().addAll(Arrays.asList(itemPedido3));
		p3.getItemPedidos().addAll(Arrays.asList(itemPedido2));
		
		itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
	}
}
