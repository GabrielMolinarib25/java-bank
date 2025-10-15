package br.com.dio.model;

import lombok.NonNull;

public record Investment(
        long Id,
        long tax,
        long initialFunds) {

    @Override
    @NonNull
    public String toString() {
        return "Investment{" +
                "Id=" + Id +
                ", tax=" + tax + "%" +
                ", initialFunds=" + (initialFunds / 100) + "," + (initialFunds % 100) +
                '}';
    }
}
