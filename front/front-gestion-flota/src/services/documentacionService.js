import api from "./api";

export const listarDocumentacion = async () => {

    const response = await api.get("/documentacion/list");

    return response.data;

};