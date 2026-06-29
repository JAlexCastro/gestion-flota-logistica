import axios from "axios";

const API = "http://localhost:8080/usuarios";

export const listarUsuarios = async () => {
    return await axios.get(`${API}/list`);
};

export const crearUsuario = async (data) => {
    return await axios.post(`${API}/save`, data);
};

export const actualizarUsuario = async (id, data) => {
    return await axios.put(`${API}/update/${id}`, data);
};

export const eliminarUsuario = async (id) => {
    return await axios.delete(`${API}/delete/${id}`);
};