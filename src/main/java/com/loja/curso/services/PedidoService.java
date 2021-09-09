package com.loja.curso.services;


import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.loja.curso.domain.Cliente;
import com.loja.curso.domain.ItemPedido;
import com.loja.curso.domain.PagamentoComBoleto;
import com.loja.curso.domain.Pedido;
import com.loja.curso.domain.Produto;
import com.loja.curso.domain.enums.EstadoPagamento;
import com.loja.curso.repositories.ItemPedidoRepository;
import com.loja.curso.repositories.PagamentoRepository;
import com.loja.curso.repositories.PedidoRepository;
import com.loja.curso.repositories.ProdutoRepository;
import com.loja.curso.security.UserSS;
import com.loja.curso.services.exception.AuthorizationException;
import com.loja.curso.services.exception.ObjectNotFoundException;


@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired 
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido findById(Integer id) throws ObjectNotFoundException {
		Optional<Pedido> optional = pedidoRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:"+ id +" Tipo: "+Pedido.class.getName()));
	}

	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setCliente(clienteService.findById(pedido.getCliente().getId()));
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamento = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamento, pedido.getInstante());
		}
		pedido = pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for (ItemPedido ip : pedido.getItemPedidos()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoRepository.findById(ip.getProduto().getId()).orElseThrow(() -> 
				new ObjectNotFoundException("Objeto não encontrado! Id:"+ ip.getProduto().getId() +" Tipo: "+Produto.class.getName())));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		
		itemPedidoRepository.saveAll(pedido.getItemPedidos());
		
		emailService.sendOrderConfirmationHtmlEmail(pedido);
		
		return pedido;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		UserSS user = UserService.authenticated();
		if( user == null ) {
			throw new AuthorizationException("Acesso negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.findById(user.getId());
		
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
	
}
