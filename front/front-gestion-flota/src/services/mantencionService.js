import axios from "axios";

const API = "http://localhost:8080/mantenciones";

export const listarMantenciones = async () => {
    return await axios.get(`${API}/list`);
};

export const crearMantencion = async (data) => {
    return await axios.post(`${API}/save`, data);
};

export const actualizarMantencion = async (id, data) => {
    return await axios.put(`${API}/update/${id}`, data);
};

export const eliminarMantencion = async (id) => {
    return await axios.delete(`${API}/delete/${id}`);
};