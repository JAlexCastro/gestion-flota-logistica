import api from "./api";

export const listarEmisiones = async () => {
    const response = await api.get("/emisiones-gases/list");
    return response.data;
};

export const crearEmision = async (data) => {
    const response = await api.post("/emisiones-gases/save", data);
    return response.data;
};


export const actualizarEmision = async (id, data) => {

    const response = await api.put(
        `/emisiones-gases/update/${id}`,
        data
    );

    return response.data;

};

export const eliminarEmision = async (id) => {
    const response = await api.delete(`/emisiones-gases/delete/${id}`);
    return response.data;
};