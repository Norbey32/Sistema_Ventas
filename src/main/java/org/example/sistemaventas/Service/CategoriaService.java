package org.example.sistemaventas.Service;

import org.example.sistemaventas.Model.Categoria;
import org.example.sistemaventas.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Long id, Categoria categoria) {
        Categoria existingCategoria = findById(id);
        existingCategoria.setNombre(categoria.getNombre());
        existingCategoria.setDescripcion(categoria.getDescripcion());
        return categoriaRepository.save(existingCategoria);
    }
    public List<Categoria> searchByNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
    }
    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }
}