import axios from "axios";
import api from "./api";

const API = "http://localhost:8080/conductores";

export const listarConductores = async () => {

    const response = await api.get("/conductores/list");

    return response.data;

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