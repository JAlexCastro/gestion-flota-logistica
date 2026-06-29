import axios from "axios";

const API = "http://localhost:8080/conductores";

export const listarConductores = async () => {
    return await axios.get(`${API}/list`);
};

export const crearConductor = async (data) => {
    return await axios.post(`${API}/save`, data);
};

export const actualizarConductor = async (id, data) => {
    return await axios.put(`${API}/update/${id}`, data);
};

export const eliminarConductor = async (id) => {
    return await axios.delete(`${API}/delete/${id}`);
};