import Table from "../../components/Table/Table";

function VehiculoTable({ vehiculos, onEdit, onDelete }) {

    return (

        <Table>

            <thead>

                <tr>

                    <th>ID</th>
                    <th>Patente</th>
                    <th>Marca</th>
                    <th>Modelo</th>
                    <th>Año</th>
                    <th>Km</th>
                    <th>Acciones</th>

                </tr>

            </thead>

            <tbody>

                {vehiculos.map(v=>(

                    <tr key={v.id}>

                        <td>{v.id}</td>
                        <td>{v.patente}</td>
                        <td>{v.marca}</td>
                        <td>{v.modelo}</td>
                        <td>{v.anio}</td>
                        <td>{v.kilometrajeActual}</td>

                        <td>

                            <div className="table-actions">

                                <button
                                    className="btn-edit"
                                    onClick={()=>onEdit(v)}
                                >
                                    Editar
                                </button>

                                <button
                                    className="btn-delete"
                                    onClick={()=>onDelete(v.id)}
                                >
                                    Eliminar
                                </button>

                            </div>

                        </td>

                    </tr>

                ))}

            </tbody>

        </Table>

    );

}

export default VehiculoTable;