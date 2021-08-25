package com.loja.curso;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.loja.curso.domain.Categoria;
import com.loja.curso.domain.Cidade;
import com.loja.curso.domain.Cliente;
import com.loja.curso.domain.Endereco;
import com.loja.curso.domain.Estado;
import com.loja.curso.domain.Pagamento;
import com.loja.curso.domain.PagamentoComBoleto;
import com.loja.curso.domain.PagamentoComCartão;
import com.loja.curso.domain.Pedido;
import com.loja.curso.domain.Produto;
import com.loja.curso.domain.enums.EstadoPagamento;
import com.loja.curso.domain.enums.TipoCliente;
import com.loja.curso.repositories.CategoriaRepository;
import com.loja.curso.repositories.CidadeRepository;
import com.loja.curso.repositories.ClienteRepository;
import com.loja.curso.repositories.EnderecoRepository;
import com.loja.curso.repositories.EstadoRepository;
import com.loja.curso.repositories.PagamentoRepository;
import com.loja.curso.repositories.PedidoRepository;
import com.loja.curso.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
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
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		
		Produto produto1 = new Produto(null, "Computador", 2000.00);
		Produto produto2 = new Produto(null, "Impressora", 800.00);
		Produto produto3 = new Produto(null, "Mouse", 80.00);
		
		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		categoria2.getProdutos().addAll(Arrays.asList(produto2));
		
		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
		produto3.getCategorias().addAll(Arrays.asList(categoria1));
		
		categoriaRepository.saveAll(Arrays.asList(categoria1,categoria2));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));
		
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");

		Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		estadoRepository.saveAll(Arrays.asList(estado1,estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1,cidade2,cidade3));
	
		Cliente cliente = new Cliente(null, "Maria Silva", "maria@gmail.com", "41141141141", TipoCliente.PESSOAFISICA);
		cliente.getTelefones().addAll(Arrays.asList("27363627","938388383"));
		
		Endereco endereco1 = new Endereco(null, "Rua", "300", "Apto 404", "Jardim", "38220834", cidade1, cliente);
		Endereco endereco2 = new Endereco(null, "Avenida", "105", "Sala 404", "Centro", "38220834", cidade2, cliente);
		
		cliente.getEnderecos().addAll(Arrays.asList(endereco1,endereco2));
		
		clienteRepository.save(cliente);
		enderecoRepository.saveAll(Arrays.asList(endereco1,endereco2));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido pedido1 = new Pedido(null, dateFormat.parse("30/09/2021 10:31"), cliente, endereco1);
		Pedido pedido2 = new Pedido(null, dateFormat.parse("10/10/2021 19:35"), cliente, endereco2);
		
		Pagamento pagamento1 = new PagamentoComCartão(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);
		
		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, dateFormat.parse("20/10/2021 00:00"), null);
		pedido2.setPagamento(pagamento2);
		
		cliente.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
		
		
	}
}
