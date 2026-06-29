import api from "./api";

export const listarSoap = async () => {
    const response = await api.get("/soap/list");
    return response.data;
};

export const crearSoap = async (data) => {
    const response = await api.post("/soap/save", data);
    return response.data;
};

export const actualizarSoap = async (id, data) => {
    const response = await api.put(`/soap/update/${id}`, data);
    return response.data;
};

export const eliminarSoap = async (id) => {
    const response = await api.delete(`/soap/delete/${id}`);
    return response.data;
};