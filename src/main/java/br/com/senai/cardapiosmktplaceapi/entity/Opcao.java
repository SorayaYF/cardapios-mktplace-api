package br.com.senai.cardapiosmktplaceapi.entity;

import java.math.BigDecimal;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "opcoes")
@Entity(name = "Opcao")
public class Opcao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Size(min = 3, max = 100, message = "O nome da opção deve conter entre 3 e 250 caracteres")
	@NotBlank(message = "O nome da opção é obrigatório")
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "descricao")
	private String descricao;
	
	@NotNull(message = "O status da opção é obrigatório")
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private Status status;
	
	@NotNull(message = "O indicador de promoção é obrigatório")
	@Enumerated(value = EnumType.STRING)
	@Column(name = "promocao")
	private Confirmacao promocao;
	
	@DecimalMin(value = "0.0", inclusive = false, message = "O percentual de desconto não pode ser inferior a 0.01%")
	@DecimalMax(value = "100.0", inclusive = false, message = "O percentual de desconto não pode ser superior a 100.00%")
	@Digits(integer = 2, fraction = 2, message = "O percentual de desconto deve possuir o formato 'NN.NN'")
	@Column(name = "percentual_desconto")
	private BigDecimal percentualDeDesconto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_categoria")
	@NotNull(message = "A categoria da opção é obrigatória")
	private Categoria categoria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_restaurante")
	@NotNull(message = "O restaurante da opção é obrigatório")
	private Restaurante restaurante;
	
	public Opcao() {
		this.status = Status.A;
	}

	@Transient
	public boolean isPersistida() {
		return getId() != null && getId() > 0;
	}

	@Transient
	public boolean isAtiva() {
		return getStatus() == Status.A;
	}

	@Transient
	public boolean isEmPromocao() {
		return getPromocao() == Confirmacao.S;
	}
}
