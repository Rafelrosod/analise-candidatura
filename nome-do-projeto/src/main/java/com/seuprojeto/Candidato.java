package com.seuprojeto;

public class Candidato {
    String nome;
    String vaga;
    int idade;
    String estado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVaga() {
        return vaga;
    }

    public void setVaga(String vaga) {
        this.vaga = vaga;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Candidato(String nome, int idade, String vaga, String estado) {
        this.nome = nome;
        this.idade = idade;
        this.vaga = vaga;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Candidato{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", vaga='" + vaga + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
