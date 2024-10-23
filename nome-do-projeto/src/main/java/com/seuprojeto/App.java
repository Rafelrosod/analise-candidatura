package com.seuprojeto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        List<Candidato> candidatos = lerCandidatos("candidatos.txt");

        /*
         * for (Candidato candidato : candidatos) {
         * System.out.println(candidato);
         * }
         */
        CalcularPorcentagem(candidatos);
        IdadeMediaPorVaga(candidatos, "QA");
        EstadosDistintos(candidatos);
        ArquivoCSV(candidatos);
    }

    public static List<Candidato> lerCandidatos(String fileName) {
        List<Candidato> candidatos = new ArrayList<>();
        try (java.io.InputStream inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                if (campos.length == 4) {
                    String nome = campos[0].trim();
                    int idade = Integer.parseInt(campos[1].trim().replaceAll("[^\\d]", ""));
                    String vaga = campos[2].trim();
                    String estado = campos[3].trim();
                    candidatos.add(new Candidato(nome, idade, vaga, estado));
                }
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return candidatos;
    }

    public static void CalcularPorcentagem(List<Candidato> candidatos) {
        Map<String, Integer> candidatosVaga = new HashMap<>();
        int totalCandidatos = candidatos.size();

        for (Candidato candidato : candidatos) {
            candidatosVaga.put(candidato.getVaga(), candidatosVaga.getOrDefault(candidato.getVaga(), 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : candidatosVaga.entrySet()) {
            String vaga = entry.getKey();
            int quantidade = entry.getValue();
            double porcantagem = (double) quantidade / totalCandidatos * 100;
            System.out.printf("%s: %.2f%% (%d candidatos)\n", vaga, porcantagem, quantidade);
        }
    }

    public static void IdadeMediaPorVaga(List<Candidato> candidatos, String vagaEspecifica) {
        int somaIdades = 0;
        int contagemCandidatos = 0;

        for (Candidato candidato : candidatos) {
            String vaga = candidato.getVaga();
            if (vaga.equalsIgnoreCase(vagaEspecifica)) {
                int idade = candidato.getIdade();
                somaIdades += idade;
                contagemCandidatos++;
            }
        }
        if (contagemCandidatos > 0) {
            double idadeMedia = (double) somaIdades / contagemCandidatos;
            System.out.printf("\nIdade média dos candidatos de %s: %.2f anos\n", vagaEspecifica, idadeMedia);
        } else {
            System.out.printf("Nenhum candidato inscrito para a vaga de %s.\n", vagaEspecifica);
        }
    }

    public static void CandidatoMaisVelhoEMaisNovo(List<Candidato> candidatos) {
        Map<String, Integer> idadeMaisVelhaPorVaga = new HashMap<>();
        Map<String, Integer> idadeMaisNovaPorVaga = new HashMap<>();

        for (Candidato candidato : candidatos) {
            String vaga = candidato.getVaga();
            int idade = candidato.getIdade();

            idadeMaisVelhaPorVaga.put(vaga,
                    Math.max(idadeMaisVelhaPorVaga.getOrDefault(vaga, Integer.MIN_VALUE), idade));
            idadeMaisNovaPorVaga.put(vaga, Math.min(idadeMaisNovaPorVaga.getOrDefault(vaga, Integer.MAX_VALUE), idade));
        }

        System.out.println("\nIdade do candidato mais velho e mais novo:");
        for (String vaga : idadeMaisVelhaPorVaga.keySet()) {
            int maisVelho = idadeMaisVelhaPorVaga.get(vaga);
            int maisNovo = idadeMaisNovaPorVaga.get(vaga);

            System.out.printf("%s - Mais velho: %d anos, Mais novo: %d anos\n", vaga, maisVelho, maisNovo);
        }
    }

    public static void SomaDasIdadesPorVaga(List<Candidato> candidatos) {
        Map<String, Integer> somaIdadesPorVaga = new HashMap<>();

        for (Candidato candidato : candidatos) {
            String vaga = candidato.getVaga();
            int idade = candidato.getIdade();

            somaIdadesPorVaga.put(vaga, somaIdadesPorVaga.getOrDefault(vaga, 0) + idade);
        }

        System.out.println("\nSoma das idades dos candidatos por vaga:");
        for (String vaga : somaIdadesPorVaga.keySet()) {
            int somaIdades = somaIdadesPorVaga.get(vaga);
            System.out.printf("%s: %d anos\n", vaga, somaIdades);
        }
    }

    public static void EstadosDistintos(List<Candidato> candidatos) {
        Set<String> estadosDistintos = new HashSet<>();

        for (Candidato candidato : candidatos) {
            estadosDistintos.add(candidato.getEstado());
        }

        System.out.println("\nNúmero de estados entre os candidatos: " + estadosDistintos.size());
    }

    public static void ArquivoCSV(List<Candidato> candidatos) {
        candidatos.sort(Comparator.comparing(Candidato::getNome));
        try (PrintWriter writer = new PrintWriter("Sorted_Academy_Candidates.csv")) {
            writer.println("Nome,Idade");
            for (Candidato candidato : candidatos) {
                writer.printf("%s,%d\n", candidato.getNome(), candidato.getIdade());
            }
            System.out.println("\nArquivo 'Sorted_Academy_Candidates.csv' criado com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo CSV: " + e.getMessage());
        }
    }
}
