import api from "./api";

export const listarConductores = async () => {
    const response = await api.get("/conductores/list");
    return response.data;
};

export const crearConductor = async (data) => {
    const response = await api.post("/conductores/save", data);
    return response.data;
};

export const actualizarConductor = async (id, data) => {
    const response = await api.put(`/conductores/update/${id}`, data);
    return response.data;
};

export const eliminarConductor = async (id) => {
    const response = await api.delete(`/conductores/delete/${id}`);
    return response.data;

};