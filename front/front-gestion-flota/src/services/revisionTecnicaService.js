import api from "./api";

export const listarRevisiones = async () => {
    const response = await api.get("/revisiones-tecnicas/list");
    return response.data;
};

export const crearRevision = async (data) => {
    const response = await api.post("/revisiones-tecnicas/save", data);
    return response.data;
};

export const actualizarRevision = async (id, data) => {
    const response = await api.put(`/revisiones-tecnicas/update/${id}`, data);
    return response.data;
};

export const eliminarRevision = async (id) => {
    const response = await api.delete(`/revisiones-tecnicas/delete/${id}`);
    return response.data;
};